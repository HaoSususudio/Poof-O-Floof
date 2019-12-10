import { Component, OnInit } from '@angular/core';
import { PhotoUrlProviderService, TestPhotoJSON } from '../services/photo-url-provider.service';
//import { FavPhotoUrlProviderService } from '../services/fav-photo-url-provider.service';
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
  private photoStreamIndexArray: Array<number>;
  private photoDisplayIndex = 0;
  private photoStreamIndex = 0;
  private mainFramePhotoUrl: string;
  private LARGE_URL_SUFFIX = '&width=600';
  // private psCurrentState: PhotoStreamMetaData;
  // private favPhotoStreamIndexArray: Array<number>;
  // private favFramePhotoUrl: string;
  // private favPhotoDisplayIndex = 0;
  // private favPhotoStreamIndex = 0;

  constructor(
    private photoUrlProvider: PhotoUrlProviderService,
    private locService: LocationService
  ) {
    // this.psCurrentState = new PhotoStreamMetaData();
    const photoStreamArraySize = this.photoUrlProvider.getMaxPhotoStreamSize();
    this.photoStreamIndexArray = [...Array(photoStreamArraySize).keys()];
    // this.shuffle(this.photoStreamIndexArray);
  }

  ngOnInit() {
    this.locService.getUserIpLocInfo().subscribe(
      userIpLoc => {
        this.photoUrlProvider.requestANewPhotoBundle(userIpLoc);
      }
    );
    // this.photoUrlProvider.requestANewPhotoBundle(this.mockIpLocInfo);
    this.setPhotoStreamCurrentState();
    this.setMainFramePhotoUrl();
    // const photoStreamArraySize = this.psCurrentState.maxStreamSize;
    // console.log(photoStreamArraySize);
  }

  nextRandomPhoto() {
    this.setMainFramePhotoUrl();
    this.photoDisplayIndex += 1;
  }

  setMainFramePhotoUrl() {
    this.photoUrlProvider.getPhotoStream()
      .subscribe(
        data => {
          this.photoStreamIndex = this.photoStreamIndexArray[this.photoDisplayIndex];
          this.mainFramePhotoUrl = data[this.photoStreamIndex].fullUrl + this.LARGE_URL_SUFFIX;
        }
      );
  }

  setPhotoStreamCurrentState() {
    this.photoUrlProvider.getPhotoStreamCurrentState()
      .subscribe(
        data => {
          // this.psCurrentState = data;
        }
      );
  }

  /**
   * Fisher–Yates shuffle algorithm, O(n) complexity
   * @param arr: Array to be shuffled
   */
  shuffle(arr: Array<any>) {
    for (let i = arr.length - 1; i > 0; i--) {
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
