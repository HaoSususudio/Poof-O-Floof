import { Component, OnInit } from '@angular/core';
import { MainPhotoComponent } from '../main-photo/main-photo.component';
import { FavPhotoUrlProviderService } from '../services/fav-photo-url-provider.service';
import {Observable, ReplaySubject, BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
export class FavoritesComponent implements OnInit {
  // private favPhotoStreamIndexArray: Array<number>;
  // private favFramePhotoUrl: string;
  // private favPhotoDisplayIndex = 0;
  // private favPhotoStreamIndex = 0;
  // private LARGE_URL_SUFFIX = '&width=600';

  constructor(
    // private photoUrlProvider: FavPhotoUrlProviderService
    ) {
      // const photoStreamArraySize = this.photoUrlProvider.getMaxFavPhotoStreamSize();
      // this.favPhotoStreamIndexArray = [...Array(photoStreamArraySize).keys()];
    }

  ngOnInit() {
    // this.setFavFramePhotoUrl();
  }

  // addToFavorites() {
  //   alert('Photo added to Favorites');
  //   this.setFavFramePhotoUrl();
  //   this.favPhotoDisplayIndex += 1;
  // }

  // setFavFramePhotoUrl() {
  //   this.photoUrlProvider.getPhotoStream()
  //     .subscribe(
  //       data => {
  //         this.favPhotoStreamIndex = this.favPhotoStreamIndexArray[this.favPhotoDisplayIndex];
  //         this.favFramePhotoUrl = data[this.favPhotoStreamIndex].fullUrl + this.LARGE_URL_SUFFIX;
  //       }
  //     );
  // }
}
