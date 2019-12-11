import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, ReplaySubject, AsyncSubject } from 'rxjs';
import { UserIpLocInfo } from '../services/models.service';
import { LocationService } from '../services/location.service';
import { AnimalPhotoJSON } from '../services/models.service';

@Injectable({ providedIn: 'root' })

export class PhotoUrlProviderService {
  private PHOTO_BUNDLE_URL = 'http://localhost:8080/Poof-O-Floof/api/location';

  private photoBundle$: BehaviorSubject<Array<AnimalPhotoJSON>>;
  private pBSize$: BehaviorSubject<number>;

  constructor(
    private http: HttpClient,
    private locService: LocationService
  ) {
    this.photoBundle$ = new BehaviorSubject<Array<AnimalPhotoJSON>>(undefined);
    this.pBSize$ = new BehaviorSubject<number>(undefined);
  }

  requestANewPhotoBundle(ipLoc: UserIpLocInfo): Observable<number> {
    console.log(ipLoc);
    if (ipLoc && ipLoc.coords) {
      this.http.post<Array<AnimalPhotoJSON>>(this.PHOTO_BUNDLE_URL, JSON.stringify(ipLoc))
        .subscribe(
          photoBundle => {
            if (photoBundle) {
              const pBSize = Object.keys(photoBundle).length;
              console.log(`Got a photo bundle from Tomcat, pBSize = ${pBSize}`);
              this.pBSize$.next(pBSize);
              this.photoBundle$.next(photoBundle);
              return this.pBSize$.asObservable();
            }
          },
          error => {
            console.log('User location is not ready. Tomcat is mad.');
            console.error(error.error);
          }
        );
    }
    return this.pBSize$.asObservable();
  }

  pubPhotoBundle(): Observable<Array<AnimalPhotoJSON>> {
    return this.photoBundle$.asObservable();
  }

  pubPBSize(): Observable<number> {
    return this.pBSize$.asObservable();
  }

}

export interface TestPhotoJSON {
  albumId: number;
  id: number;
  title: string;
  url: string;
  thumbnailUrl: string;
}
