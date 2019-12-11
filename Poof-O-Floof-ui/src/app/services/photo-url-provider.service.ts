import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, ReplaySubject, AsyncSubject } from 'rxjs';
import { UserIpLocInfo } from '../services/models.service';
import { LocationService } from '../services/location.service';
import { AnimalPhotoJSON, FavPhotoJSON } from '../services/models.service';

@Injectable({ providedIn: 'root' })

export class PhotoUrlProviderService {
  private PHOTO_BUNDLE_URL = 'http://localhost:8080/Poof-O-Floof/api/location';
  private FAV_URL = 'http://localhost:8080/Poof-O-Floof/api/favorite?userId=';
  private photoBundle$: BehaviorSubject<Array<AnimalPhotoJSON>>;
  private pBSize$: BehaviorSubject<number>;
  private favPhotoBundle$: BehaviorSubject<Array<FavPhotoJSON>>;


  constructor(
    private http: HttpClient
  ) {
    this.favPhotoBundle$ = new BehaviorSubject<Array<FavPhotoJSON>>(undefined);
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

  pubFavorites(userId: number): Observable<Array<FavPhotoJSON>> {
    this.http.get<Array<FavPhotoJSON>>(this.FAV_URL + userId)
      .subscribe(
        photoBundle => {
          if (photoBundle) {
            const pBSize = Object.keys(photoBundle).length;
            console.log(`Got a favorite photo bundle from Tomcat.`);
            this.favPhotoBundle$.next(photoBundle);
            return this.favPhotoBundle$.asObservable();
          }
        },
        error => {
          console.error(error.error);
        }
      );
    return this.favPhotoBundle$.asObservable();
  }

  pubPhotoBundle(): Observable<Array<AnimalPhotoJSON>> {
    return this.photoBundle$.asObservable();
  }

  pubPBSize(): Observable<number> {
    return this.pBSize$.asObservable();
  }

}
