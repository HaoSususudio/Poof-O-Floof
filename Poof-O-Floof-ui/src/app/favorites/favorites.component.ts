import { Component, OnInit } from '@angular/core';
import { PhotoUrlProviderService } from '../services/photo-url-provider.service';
import { Observable, ReplaySubject, BehaviorSubject } from 'rxjs';
import { ConditionalExpr } from '@angular/compiler';
import { NgxGalleryOptions, NgxGalleryImage, NgxGalleryAnimation } from 'ngx-gallery';
import { HttpClient, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
export class FavoritesComponent implements OnInit {
  favPhotoStreamIndexArray: Array<number>;
  favFramePhotoUrl: string;
  private mockUserId = 525252;
  galleryOptions: NgxGalleryOptions[];
  galleryImages: NgxGalleryImage[];

  constructor(
    private http: HttpClient,
    private photoUrlProvider: PhotoUrlProviderService
  ) {
    this.galleryImages = [];
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

    this.subFavorites();
  }

  subFavorites() {
    this.photoUrlProvider.pubFavorites(this.mockUserId).subscribe(
      favPhotos => {
        this.galleryImages = favPhotos;
      }
    );
  }

}
