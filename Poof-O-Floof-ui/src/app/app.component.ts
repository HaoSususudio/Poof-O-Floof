import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LocationService } from './services/location.service';
import { UserIpLocInfo } from './services/models.service';
import { PhotoUrlProviderService } from './services/photo-url-provider.service';
import { Observable } from 'rxjs';
// import { NgxGalleryOptions, NgxGalleryImage, NgxGalleryAnimation } from 'ngx-gallery';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  title = 'Poof-O-Floof';
  userIpLocInfo: UserIpLocInfo;
 // galleryOptions: NgxGalleryOptions[];
 // galleryImages: NgxGalleryImage[];

  constructor(
    // private http: HttpClient,
    private locService: LocationService,
    // private photoUrlProvider: PhotoUrlProviderService
  ) { this.subPhotoUrlProvider(); }

  ngOnInit(): void { this.subPhotoUrlProvider(); }

  subPhotoUrlProvider() {
    this.locService.pubUserIpLocInfo().subscribe(
      (ipLoc) => {
        this.userIpLocInfo = ipLoc;
      }
    );
  }
}
