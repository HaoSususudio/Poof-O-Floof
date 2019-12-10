import { Component, OnInit } from '@angular/core';
import { PhotoUrlProviderService, TestPhotoJSON } from '../services/photo-url-provider.service';
// import { FavPhotoUrlProviderService } from '../services/fav-photo-url-provider.service';
import { PhotoStreamMetaData, UserIpLocInfo } from '../services/models.service';
import { ReplaySubject, BehaviorSubject } from 'rxjs';
import { ConditionalExpr } from '@angular/compiler';
import { LocationService } from '../services/location.service';
import { AnimalPhotoJSON } from '../services/models.service';

@Component({
  selector: 'app-main-photo',
  templateUrl: './main-photo.component.html',
  styleUrls: ['./main-photo.component.css']
})

export class MainPhotoComponent implements OnInit {
  private photoStream: AnimalPhotoJSON[];
  private psIndexArray: number[];
  private photoDisplayIndex = 0;
  private photoStreamIndex = 0;
  private totPhotoNum = 0;
  // private psCurrentState: PhotoStreamMetaData;

  mainFramePhotoUrl: string;
  mainFramePhotoType: string;
  totalPhotoNumber: number;
  lastBundleSize: number;
  private PHOTO_RESERVE_SIZE = 5;
  private LARGE_URL_SUFFIX = '&width=600';

  constructor(
    private locService: LocationService,
    private photoUrlProvider: PhotoUrlProviderService,
  ) {
    const psArraySize = this.photoUrlProvider.getMaxPhotoStreamSize();
    this.psIndexArray = [...Array(psArraySize).keys()];
    this.photoStream = [];
    this.psIndexArray = [];
    // this.psCurrentState = new PhotoStreamMetaData();
  }

  ngOnInit() {
    console.log('Echo from ngOnInit');
    this.photoUrlProvider.publishPhotoBundle().subscribe(
      pB => {
        if (pB && pB.length) {
          console.log(pB);
          this.photoStream = this.photoStream.concat(pB);
        }
      });
    this.photoUrlProvider.publishPBSize().subscribe(
      pdSize => {
        if (pdSize) {
          console.log('pdSize: ' + pdSize);
          this.psIndexArray = this.psIndexArray.concat(this.shuffle(this.makeIntArr(this.totPhotoNum, pdSize)));
          this.totPhotoNum += pdSize;
          console.log('totPhotoNum: ' + this.totPhotoNum);
        }
      });
  }

  addMorePhotos() {
    this.photoUrlProvider.requestANewPhotoBundle();
    // .subscribe(
    //   pdSize => {
    //     if (pdSize) {
    //       console.log('pdSize: ' + pdSize);
    //     }
    //   }
    // );

    // .subscribe(
    //   pscs => {
    //     if (pscs) {
    //       this.totalPhotoNumber = pscs.totPhotoNum;
    //       this.lastBundleSize = pscs.totPhotoNum;
    //       const shuffleStart = pscs.totPhotoNum - pscs.lastBundleSize;
    //       const shuffleEnd = pscs.totPhotoNum - 1;
    //       this.shuffle(this.psIndexArray, shuffleStart, shuffleEnd);
    //       console.log(this.psIndexArray);
    //     }
    //   });
  }

  printPS() {
    // console.log(this.photoStream);
    console.log(this.psIndexArray);
    // console.log(this.shuffle(this.makeIntArr(0, 5)));
  }

  nextRandomPhoto() {
    this.setMainFramePhotoUrl();
    this.photoDisplayIndex += 1;
    if (this.photoDisplayIndex < this.totalPhotoNumber - this.PHOTO_RESERVE_SIZE) {
      this.addMorePhotos();
    }
  }

  setMainFramePhotoUrl() {
    this.photoStreamIndex = this.psIndexArray[this.photoDisplayIndex];
    this.mainFramePhotoUrl = this.photoStream[this.photoStreamIndex].fullUrl + this.LARGE_URL_SUFFIX;
    this.mainFramePhotoType = this.photoStream[this.photoStreamIndex].type;
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

  makeIntArr(start: number, length: number): Array<number> {
    const arr = [];
    for (let i = start; i < start + length; i++) {
      arr.push(i);
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
