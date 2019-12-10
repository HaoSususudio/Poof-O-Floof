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
  private MAX_PHOTO_STREAM_SIZE = 200;
  private photoBundleSize: number;
  private userIpLocInfo: UserIpLocInfo;

  private photoStream$: ReplaySubject<AnimalPhotoJSON>;
  private photoBundle$: BehaviorSubject<Array<AnimalPhotoJSON>>;
  private psCurrentState: PhotoStreamMetaData;
  private psCurrentState$: BehaviorSubject<PhotoStreamMetaData>;

  constructor(
    private http: HttpClient,
    private locService: LocationService
  ) {
    this.locService.getUserIpLocInfo().subscribe(ipLoc => this.userIpLocInfo = ipLoc);
    // this.photoStream$ = new ReplaySubject<Array<AnimalPhotoJSON>>(this.MAX_PHOTO_STREAM_SIZE);
    this.photoBundle$ = new BehaviorSubject<Array<AnimalPhotoJSON>>(undefined);
    this.psCurrentState = new PhotoStreamMetaData();
    this.psCurrentState.totPhotoNum = 0;
    this.psCurrentState$ = new BehaviorSubject<PhotoStreamMetaData>(undefined);


  }

  getMaxPhotoStreamSize() {
    return this.MAX_PHOTO_STREAM_SIZE;
  }

  requestANewPhotoBundle(): number {
    this.http.post<Array<AnimalPhotoJSON>>(this.PHOTO_BUNDLE_URL, JSON.stringify(this.userIpLocInfo))
      .subscribe(
        photoBundle => {
          const size = Object.keys(photoBundle).length;
          console.log('Got photo bundle gotten from Tomcat');
          console.log('Size: ' + Object.keys(photoBundle).length);
          this.photoBundle$.next(photoBundle);
          return size;
        },
        error => {
          console.log('User location is not ready. Tomcat is mad.');
          // console.error(error.error);
        }
      );
    return undefined;
  }

  getPhotoStream(): Observable<AnimalPhotoJSON> {
    return this.photoStream$.asObservable();
  }

  getPSCurrentState(): Observable<PhotoStreamMetaData> {
    return this.psCurrentState$.asObservable();
  }

  signUpPhotoBundle(): Observable<Array<AnimalPhotoJSON>> {
    return this.photoBundle$.asObservable();
  }

}

export interface TestPhotoJSON {
  albumId: number;
  id: number;
  title: string;
  url: string;
  thumbnailUrl: string;
}
