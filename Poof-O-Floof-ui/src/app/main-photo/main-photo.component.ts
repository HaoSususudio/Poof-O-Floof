import { Component, OnInit } from '@angular/core';
import { PhotoUrlProviderService, TestPhotoJSON } from '../services/photo-url-provider.service';
import { PhotoStreamMetaData, UserIpLocInfo } from '../services/models.service';
import { ReplaySubject, BehaviorSubject } from 'rxjs';
import { ConditionalExpr } from '@angular/compiler';
import { LocationService } from '../services/location.service';

@Component({
  selector: 'app-main-photo',
  templateUrl: './main-photo.component.html',
  styleUrls: ['./main-photo.component.css']
})

export class MainPhotoComponent implements OnInit {
  private psIndexArray: Array<number>;
  private photoDisplayIndex = 0;
  private photoStreamIndex = 0;
  private mainFramePhotoUrl: string;
  private mainFramePhotoType: string;
  private LARGE_URL_SUFFIX = '&width=600';
  psCurrentState: PhotoStreamMetaData;

  constructor(
    private locService: LocationService,
    private photoUrlProvider: PhotoUrlProviderService,
  ) {
    this.psCurrentState = new PhotoStreamMetaData();
    const psArraySize = this.photoUrlProvider.getMaxPhotoStreamSize();
    const newBundleStartIndex = 0;
    const newBundleEndIndex = 0;
    this.psIndexArray = [...Array(psArraySize).keys()];
  }

  ngOnInit() {
    this.addMorePhotos();
    this.setMainFramePhotoUrl();
  }

  addMorePhotos() {
    this.locService.getUserIpLocInfo()
      .subscribe(
        ipLoc => {
          this.photoUrlProvider.requestANewPhotoBundle(ipLoc)
            .subscribe(
              pscs => {
                if (pscs !== undefined) {
                  this.psCurrentState = pscs;
                  this.shuffle(this.psIndexArray, pscs.totPhotoNum - pscs.lastBundleSize, pscs.totPhotoNum - 1);
                  console.log(this.psIndexArray);
                }
              });
        });
  }

  nextRandomPhoto() {
    console.log(this.psIndexArray);
    this.photoDisplayIndex += 1;
    this.setMainFramePhotoUrl();
  }

  setMainFramePhotoUrl() {
    this.photoUrlProvider.getPhotoStream()
      .subscribe(
        data => {
          this.photoStreamIndex = this.psIndexArray[this.photoDisplayIndex];
          this.mainFramePhotoUrl = data[this.photoStreamIndex].fullUrl + this.LARGE_URL_SUFFIX;
          this.mainFramePhotoType = data[this.photoStreamIndex].type;
        }
      );
  }

  setPhotoStreamCurrentState() {
    this.photoUrlProvider.getPhotoStreamCurrentState()
      .subscribe(
        data => {
          this.psCurrentState = data;
        }
      );
  }

  /**
   * Fisher–Yates shuffle algorithm, O(n) complexity
   * @param arr: Array to be shuffled
   */
  shuffle(arr: Array<any>, startIndex: number, endIndex: number) {
    for (let i = endIndex; i > startIndex; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [arr[i], arr[j]] = [arr[j], arr[i]];
    }
    return arr;
  }
}
