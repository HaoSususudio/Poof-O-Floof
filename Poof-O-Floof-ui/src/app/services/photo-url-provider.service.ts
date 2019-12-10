import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, BehaviorSubject, ReplaySubject, AsyncSubject } from 'rxjs';
import { UserIpLocInfo, PhotoStreamMetaData } from '../services/models.service';
import { LocationService } from '../services/location.service';

@Injectable({ providedIn: 'root' })

export class PhotoUrlProviderService {
  // private TEST_PHOTO_BUNDLE_URL = 'https://jsonplaceholder.typicode.com/photos?albumId=1';
  private PHOTO_BUNDLE_URL = 'http://localhost:8080/Poof-O-Floof/api/location';
  private MAX_PHOTO_STREAM_SIZE = 500;
  private photoBundleSize: number;
  private userIpLocInfo: UserIpLocInfo;

  private photoStream$: ReplaySubject<AnimalPhotoJSON>;
  private psCurrentState: PhotoStreamMetaData;
  private psCurrentState$: BehaviorSubject<PhotoStreamMetaData>;

  constructor(
    private http: HttpClient,
    private locService: LocationService
  ) {
    this.locService.getUserIpLocInfo();
    this.photoStream$ = new ReplaySubject<AnimalPhotoJSON>(this.MAX_PHOTO_STREAM_SIZE);
    this.psCurrentState = new PhotoStreamMetaData();
    this.psCurrentState.totPhotoNum = 0;
    this.psCurrentState$ = new BehaviorSubject<PhotoStreamMetaData>(undefined);
  }

  getMaxPhotoStreamSize() {
    return this.MAX_PHOTO_STREAM_SIZE;
  }

  requestANewPhotoBundle(ipLoc: UserIpLocInfo): Observable<PhotoStreamMetaData> {
    this.http.post<AnimalPhotoJSON>(this.PHOTO_BUNDLE_URL, JSON.stringify(ipLoc))
      .subscribe(
        data => {
          this.psCurrentState.lastBundleSize = Object.keys(data).length;
          this.psCurrentState.totPhotoNum += this.psCurrentState.lastBundleSize;
          this.psCurrentState$.next(this.psCurrentState);
          this.photoStream$.next(data);
        },
        error => {
          console.log('User location is not ready. Tomcat is mad.');
          // console.error(error.error);
        }
      );
    return this.psCurrentState$.asObservable();
  }

  getPhotoStream(): Observable<AnimalPhotoJSON> {
    return this.photoStream$.asObservable();
  }

  getPhotoStreamCurrentState(): Observable<PhotoStreamMetaData> {
    return this.psCurrentState$.asObservable();
  }
}

export interface AnimalPhotoJSON {
  animalId: number;
  photoId: number;
  type: string;
  fullUrl: string;
}

export interface TestPhotoJSON {
  albumId: number;
  id: number;
  title: string;
  url: string;
  thumbnailUrl: string;
}
