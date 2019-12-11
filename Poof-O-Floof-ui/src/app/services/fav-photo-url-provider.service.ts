import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, BehaviorSubject, ReplaySubject, AsyncSubject } from 'rxjs';
import { PhotoStreamMetaData } from '../services/models.service';

@Injectable({ providedIn: 'root' })

export class FavPhotoUrlProviderService {
  private FAV_PHOTO_BUNDLE_URL = 'http://localhost:8080/Poof-O-Floof/api/favorite?userId=525252';
 // private TEST_PHOTO_BUNDLE_URL = 'https://jsonplaceholder.typicode.com/photos?albumId=1';

// private TEST_PHOTO_BUNDLE_URL = 'https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/44378531/1/?bust=1575686254';
 private photoStream$: BehaviorSubject<Array<FavoritePhotoJSON>>;

 constructor(
  private http: HttpClient
 ) {
//  this.photoStream$ = new ReplaySubject<AnimalPhotoJSON>();
  this.photoStream$ = new BehaviorSubject<Array<FavoritePhotoJSON>>(undefined);
  }

  requestFavoritePhoto() {
    this.http.get<Array<FavoritePhotoJSON >>(this.FAV_PHOTO_BUNDLE_URL)
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

export interface FavoritePhotoJSON {
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

