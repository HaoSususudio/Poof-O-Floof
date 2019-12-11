import { Component, OnInit } from '@angular/core';
import { PhotoUrlProviderService } from '../services/photo-url-provider.service';
// import { FavPhotoUrlProviderService } from '../services/fav-photo-url-provider.service';
import { LocationService } from '../services/location.service';
import { AnimalPhotoJSON } from '../services/models.service';
import { HttpClient, HttpResponse } from '@angular/common/http';

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
  adoptionUrl: string;
  mainFramePhotoType: string;
  private PHOTO_RESERVE_SIZE = 20;
  private LARGE_URL_SUFFIX = '&width=600';

  private FAV_POST_URL = 'http://localhost:8080/Poof-O-Floof/api/favorite?userId=';
  private mockUserId = 525252;

  constructor(
    private locService: LocationService,
    private photoUrlProvider: PhotoUrlProviderService,
    private http: HttpClient
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
            console.log('Main photo url not yet set. Now setting it.');
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
    if (this.totPhotoNum - this.photoDisplayIndex === this.PHOTO_RESERVE_SIZE) {
      this.addMorePhotos();
    }
    this.photoDisplayIndex += 1;
    this.setMainFramePhotoUrl();
  }

  setMainFramePhotoUrl() {
    this.photoStreamIndex = this.psIndexArray[this.photoDisplayIndex];
    this.mainFramePhotoUrl = this.photoStream[this.photoStreamIndex].fullUrl + this.LARGE_URL_SUFFIX;
    this.mainFramePhotoType = this.photoStream[this.photoStreamIndex].type;
    this.adoptionUrl = this.photoStream[this.photoStreamIndex].url;
  }

  addToFavorites() {
    console.log(this.photoStream[this.photoStreamIndex]);
    console.log(this.FAV_POST_URL + this.mockUserId);
    const fullFavPostUrl = this.FAV_POST_URL + this.mockUserId;
    this.http.post(fullFavPostUrl, JSON.stringify(this.photoStream[this.photoStreamIndex])).subscribe(
      success => {
        alert('Photo added to Favorites');
      },
      error => {
        console.error(error);
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

  makeIntArr(start: number, length: number): Array<number> {
    const arr = [];
    for (let i = start; i < start + length; i++) {
      arr.push(i);
    }
    return arr;
  }

}
