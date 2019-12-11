import { Component, OnInit } from '@angular/core';
import { PhotoUrlProviderService, TestPhotoJSON } from '../services/photo-url-provider.service';
// import { FavPhotoUrlProviderService } from '../services/fav-photo-url-provider.service';
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
  totPhotoNum = 0;
  photoBundleSize = 0;

  mainFramePhotoUrl: string;
  mainFramePhotoType: string;
  private PHOTO_RESERVE_SIZE = 5;
  private LARGE_URL_SUFFIX = '&width=600';

  /* favPhotoStreamIndexArray: Array<number>;
  favPhotoDisplayIndex = 0;
  favPhotoStreamIndex = 0;
  favFramePhotoUrl: string; */

  constructor(
    private locService: LocationService,
    private photoUrlProvider: PhotoUrlProviderService,
    // private favPhotoUrlProvider: FavPhotoUrlProviderService,
  ) {
    this.photoStream = [];
    this.psIndexArray = [];
  }

  ngOnInit() {
    this.subPhotoBundle();
    this.subPBSize();
    this.addMorePhotos();
  }

  subPhotoBundle() {
    this.photoUrlProvider.pubPhotoBundle().subscribe(
      pB => {
        if (pB && pB.length) {
          this.photoStream = this.photoStream.concat(pB);
          if (!this.mainFramePhotoUrl) {
            console.log('Main photo url not yes set. Now setting it.');
            this.setMainFramePhotoUrl();
          }
        }
      });
  }

  subPBSize() {
    this.photoUrlProvider.pubPBSize().subscribe(
      pBSize => {
        if (pBSize) {
          this.photoBundleSize = pBSize;
          this.psIndexArray = this.psIndexArray.concat(this.shuffle(this.makeIntArr(this.totPhotoNum, pBSize)));
          this.totPhotoNum += pBSize;
        }
      });
  }

  addMorePhotos() {
    this.locService.pubUserIpLocInfo().subscribe(
      ipLoc => {
        this.photoUrlProvider.requestANewPhotoBundle(ipLoc);
      });
  }

  nextRandomPhoto() {
    if (this.photoDisplayIndex === this.totPhotoNum - this.PHOTO_RESERVE_SIZE) {
      this.addMorePhotos();
    }
    this.photoDisplayIndex += 1;
    this.setMainFramePhotoUrl();
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
  /* addToFavorites() {
    alert('Photo added to Favorites');
    this.setFavFramePhotoUrl();
    this.favPhotoDisplayIndex += 1;
  }


  setFavFramePhotoUrl() {
    this.favPhotoUrlProvider.getPhotoStream()
      .subscribe(
        data => {
          this.favPhotoStreamIndex = this.favPhotoStreamIndexArray[this.favPhotoDisplayIndex];
          this.favFramePhotoUrl = data[this.favPhotoStreamIndex].url + this.LARGE_URL_SUFFIX;
        } //hard code user and json file and push to server side
      );
  } */
}
