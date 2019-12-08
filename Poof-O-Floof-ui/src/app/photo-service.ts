import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, ReplaySubject } from 'rxjs';

@Injectable()
export class PhotoService {

    private currentPhoto$: ReplaySubject<PhotoInfo> = new ReplaySubject<PhotoInfo>();

    setCurrentPhotl(photoInfo: PhotoInfo): void {
        this.currentPhoto$.next(photoInfo);
    }

    getCurrentPhoto(): Observable<PhotoInfo> {
        return this.currentPhoto$.asObservable();
    }
}

export interface PhotoInfo {
    photoId: number;
    animalId: number;
    //photoUrl: string; //size
    // smallPhotoUrl: string;
    // mediumPhotoUrl: string;
    //largePhotoUrl: string;
    fullPhotoUrl: string;

}
