import { Component, OnInit } from '@angular/core';
import { PhotoUrlProviderService, TestPhotoJSON } from '../services/photo-url-provider.service';
// import { FavPhotoUrlProviderService } from '../services/fav-photo-url-provider.service';
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
  mainFramePhotoUrl: string;
  mainFramePhotoType: string;
  private LARGE_URL_SUFFIX = '&width=600';

  constructor(
    private locService: LocationService,
    private photoUrlProvider: PhotoUrlProviderService,
  ) {
    const psArraySize = this.photoUrlProvider.getMaxPhotoStreamSize();
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
                  // this.psCurrentState = pscs;
                  this.shuffle(this.psIndexArray, pscs.totPhotoNum - pscs.lastBundleSize, pscs.totPhotoNum - 1);
                }
              });
        });
  }

  nextRandomPhoto() {
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

  // addToFavorites() {
  //   alert('Photo added to Favorites');
  //   this.setFavFramePhotoUrl();
  //   this.favPhotoDisplayIndex += 1;
  // }

  // setFavFramePhotoUrl() {
  //   this.photoUrlProvider.getPhotoStream()
  //     .subscribe(
  //       data => {
  //         this.favPhotoStreamIndex = this.favPhotoStreamIndexArray[this.favPhotoDisplayIndex];
  //         this.favFramePhotoUrl = data[this.favPhotoStreamIndex].fullUrl + this.LARGE_URL_SUFFIX;
  //       }
  //     );
  // }
}
