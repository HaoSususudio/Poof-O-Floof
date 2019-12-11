import { Component, OnInit } from '@angular/core';
import { MainPhotoComponent } from '../main-photo/main-photo.component';
import { PhotoUrlProviderService, TestPhotoJSON } from '../services/photo-url-provider.service';
import { FavPhotoUrlProviderService } from '../services/fav-photo-url-provider.service';
import {Observable, ReplaySubject, BehaviorSubject } from 'rxjs';
import { ConditionalExpr } from '@angular/compiler';
import { NgxGalleryOptions, NgxGalleryImage, NgxGalleryAnimation } from 'ngx-gallery';
import { testPhotos } from '../testPhotos';
import { HttpClient, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
export class FavoritesComponent implements OnInit {
  /* private LARGE_URL_SUFFIX = '&width=600';
  favPhotoStreamIndexArray: Array<number>;
  favFramePhotoUrl: string;
  favPhotoDisplayIndex = 0;
  favPhotoStreamIndex = 0;*/
  galleryOptions: NgxGalleryOptions[];
  galleryImages: NgxGalleryImage[];

  constructor(
    private http: HttpClient
  // private photoUrlProvider: FavPhotoUrlProviderService
  // private photoUrlProvider: PhotoUrlProviderService,
    ) {
    /*  const photoStreamArraySize = this.photoUrlProvider;
      this.favPhotoStreamIndexArray = [...Array().keys()];*/
    }

  ngOnInit() {
  // this.setFavFramePhotoUrl();
  this.galleryOptions = [
    {
        width: '600px',
        height: '800px',
        thumbnailsColumns: 4,
        imageAnimation: NgxGalleryAnimation.Slide
    },
       // max-width 800
       {
        breakpoint: 100,
        width: '100%',
        height: '600px',
        imagePercent: 80,
        thumbnailsPercent: 50,
        thumbnailsMargin: 50,
        thumbnailMargin: 50
    },
        // max-width 400
        {
          breakpoint: 100,
          preview: false
      }
  ];

  this.galleryImages = [
    {
      small: 'https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/46728967/1/?bust=1575244003',
      medium: 'https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/46728967/1/?bust=1575244003',
      big: 'https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/46728967/1/?bust=1575244003'
    },
    {
      small: 'https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/46728967/2/?bust=1575244003',
      medium: 'https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/46728967/2/?bust=1575244003',
      big: 'https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/46728967/2/?bust=1575244003'
    },
    {
      small: 'https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/46728966/1/?bust=1575244003',
      medium: 'https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/46728966/1/?bust=1575244003',
      big: 'https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/46728966/1/?bust=1575244003'
    }
  ];
  }

   /*addToFavorites() {
     alert('Photo added to Favorites');
     this.setFavFramePhotoUrl();
     this.favPhotoDisplayIndex += 1;
   }

   setFavFramePhotoUrl() {
     this.photoUrlProvider.getPhotoStream()
       .subscribe(
         data => {
           this.favPhotoStreamIndex = this.favPhotoStreamIndexArray[this.favPhotoDisplayIndex];
           this.favFramePhotoUrl = data[this.favPhotoStreamIndex].url + this.LARGE_URL_SUFFIX;
         }
       );
   }*/
}
