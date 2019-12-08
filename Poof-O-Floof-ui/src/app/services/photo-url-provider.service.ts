import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, BehaviorSubject, ReplaySubject, AsyncSubject } from 'rxjs';
import { PhotoStreamMetaData } from '../services/models.service';

@Injectable({ providedIn: 'root' })

export class PhotoUrlProviderService {
  private TEST_PHOTO_BUNDLE_URL = 'https://jsonplaceholder.typicode.com/photos?albumId=1';
  private PHOTO_BUNDLE_URL = 'http://poof-o-floof/api';
  private MAX_PHOTO_STREAM_SIZE = 50;
  private photoBundleSize: number;

  private photoStream$ = new ReplaySubject<TestPhotoJSON>(this.MAX_PHOTO_STREAM_SIZE);
  private photoStreamCurrentState = new PhotoStreamMetaData();
  private photoStreamCurrentState$ = new BehaviorSubject<PhotoStreamMetaData>(undefined);

  constructor(private http: HttpClient) {
    this.photoStreamCurrentState.maxStreamSize = this.MAX_PHOTO_STREAM_SIZE;
    this.requestANewPhotoBundle();
  }

  getMaxPhotoStreamSize() {
    return this.MAX_PHOTO_STREAM_SIZE;
  }

  requestANewPhotoBundle() {
    this.http.get<TestPhotoJSON>(this.TEST_PHOTO_BUNDLE_URL)
      .subscribe(data => {
        this.photoStreamCurrentState.lastPhotoBundleSize = Object.keys(data).length;
        this.photoStreamCurrentState$.next(this.photoStreamCurrentState);
        this.photoStream$.next(data);
      });
  }

  getPhotoStream() {
    return this.photoStream$.asObservable();
  }

  getPhotoStreamCurrentState() {
    return this.photoStreamCurrentState$.asObservable();
  }
  
}

export interface PhotoBundleJSON {
  animalId: string;
}

export interface TestPhotoJSON {
  albumId: number;
  id: number;
  title: string;
  url: string;
  thumbnailUrl: string;
}
