import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, BehaviorSubject, ReplaySubject, AsyncSubject } from 'rxjs';
import { PhotoStreamMetaData } from '../services/models.service';

@Injectable({ providedIn: 'root' })

export class FavPhotoUrlProviderService {
 // private PHOTO_BUNDLE_URL = 'http://localhost:8080/Poof-O-Floof/api/favorite?userId=5252';
 // private TEST_PHOTO_BUNDLE_URL = 'https://jsonplaceholder.typicode.com/photos?albumId=1';

 private TEST_PHOTO_BUNDLE_URL = 'https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/44378531/1/?bust=1575686254';
// private photoStream$: ReplaySubject<TestPhotoJSON>;
 private photoStream$: ReplaySubject<AnimalPhotoJSON>;
 private psCurrentState: PhotoStreamMetaData;
 private psCurrentState$: BehaviorSubject<PhotoStreamMetaData>;

 constructor(
  private http: HttpClient
 ) {
  this.photoStream$ = new ReplaySubject<AnimalPhotoJSON>();
  this.psCurrentState = new PhotoStreamMetaData();
  this.psCurrentState.totPhotoNum = 0;
  this.psCurrentState$ = new BehaviorSubject<PhotoStreamMetaData>(undefined);
  // this.requestFavoritePhoto();
  }

  /* requestFavoritePhoto() {
    this.http.get<TestPhotoJSON>(this.TEST_PHOTO_BUNDLE_URL)
      .subscribe(
        data => {
         // this.psCurrentState$.next(this.psCurrentState);
          this.photoStream$.next(data);
        },
        error => {
          console.log('User location is not ready. Tomcat is mad.');
        }
      );
  } */

  requestFavoritePhoto() {
    this.http.get<AnimalPhotoJSON>(this.TEST_PHOTO_BUNDLE_URL)
      .subscribe(
        data => {
         // this.psCurrentState$.next(this.psCurrentState);
          this.photoStream$.next(data);
        },
        error => {
          console.log('User location is not ready. Tomcat is mad.');
        }
      );
  }
  getPhotoStream() {
     return this.photoStream$.asObservable();
  }
}

export interface AnimalPhotoJSON {
  animalId: number;
  photoId: number;
  type: string;
  smallUrl: string;
  mediumUrl: string;
  bigUrl: string; // big instead of large to match npm-gallery package format
}

export interface TestPhotoJSON {
  albumId: number;
  id: number;
  title: string;
  url: string;
  thumbnailUrl: string;
}

