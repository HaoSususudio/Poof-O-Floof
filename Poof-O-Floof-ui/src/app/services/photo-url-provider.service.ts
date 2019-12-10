import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, BehaviorSubject, ReplaySubject, AsyncSubject } from 'rxjs';
import { UserIpLocInfo, PhotoStreamMetaData } from '../services/models.service';
import { LocationService } from '../services/location.service';
import { AnimalPhotoJSON } from '../services/models.service';

@Injectable({ providedIn: 'root' })

export class PhotoUrlProviderService {
  // private TEST_PHOTO_BUNDLE_URL = 'https://jsonplaceholder.typicode.com/photos?albumId=1';
  private PHOTO_BUNDLE_URL = 'http://localhost:8080/Poof-O-Floof/api/location';
  // private MAX_PHOTO_STREAM_SIZE = 200;
  private photoBundleSize: number;
  userIpLocInfo: UserIpLocInfo;

  private photoBundle$: BehaviorSubject<Array<AnimalPhotoJSON>>;
  private pBSize$: BehaviorSubject<number>;

  constructor(
    private http: HttpClient,
    private locService: LocationService
  ) {
    this.photoBundle$ = new BehaviorSubject<Array<AnimalPhotoJSON>>(undefined);
    this.pBSize$ = new BehaviorSubject<number>(undefined);
  }

  requestANewPhotoBundle(ipLoc: UserIpLocInfo) {
    console.log('ipLoc: ' + ipLoc);
    this.http.post<Array<AnimalPhotoJSON>>(this.PHOTO_BUNDLE_URL, JSON.stringify(ipLoc))
      .subscribe(
        photoBundle => {
          if (photoBundle) {
            const pBSize = Object.keys(photoBundle).length;
            console.log('Got photo bundle gotten from Tomcat');
            console.log('Size: ' + pBSize);
            this.pBSize$.next(pBSize);
            this.photoBundle$.next(photoBundle);
          }
        },
        error => {
          console.log('User location is not ready. Tomcat is mad.');
          // console.error(error.error);
        }
      );

  }

  pubPhotoBundle(): Observable<Array<AnimalPhotoJSON>> {
    return this.photoBundle$.asObservable();
  }

  pubPBSize(): Observable<number> {
    return this.pBSize$.asObservable();
  }

  // setUserIpLocInfo(ipLoc: UserIpLocInfo) {
  //   this.userIpLocInfo = ipLoc;
  // }
  // getUserIpLocInfo() {
  //   return this.userIpLocInfo;
  // }
}

export interface TestPhotoJSON {
  albumId: number;
  id: number;
  title: string;
  url: string;
  thumbnailUrl: string;
}
