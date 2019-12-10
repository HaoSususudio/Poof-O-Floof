import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, BehaviorSubject, ReplaySubject, AsyncSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })

export class FavPhotoUrlProviderService {
  private PHOTO_BUNDLE_URL = 'http://localhost:8080/Poof-O-Floof/api/location';

  constructor(
  ) {
   }
}

export interface AnimalPhotoJSON {
  animalId: number;
  photoId: number;
  type: string;
  fullUrl: string;
}

