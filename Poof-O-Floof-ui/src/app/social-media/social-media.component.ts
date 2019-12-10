import { Component, OnInit } from '@angular/core';
import { MainPhotoComponent } from '../main-photo/main-photo.component';
import { PhotoUrlProviderService, TestPhotoJSON } from '../services/photo-url-provider.service';
import { ReplaySubject, BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-social-media',
  templateUrl: './social-media.component.html',
  styleUrls: ['./social-media.component.css']
})
export class SocialMediaComponent implements OnInit {
  private photoStreamIndexArray: Array<number>;
  private photoDisplayIndex = 0;
  private photoStreamIndex = 0;
  private mainFramePhotoUrl: string;
  private LARGE_URL_SUFFIX = '&width=600';

  constructor(
    private photoUrlProvider: PhotoUrlProviderService
  ) {
    // const photoStreamArraySize = this.photoUrlProvider.getMaxPhotoStreamSize();
    // this.photoStreamIndexArray = [...Array(photoStreamArraySize).keys()];
  }

  ngOnInit() {
    this.setMainFramePhotoUrl();
  }

  setMainFramePhotoUrl() {
    // this.photoUrlProvider.getPhotoStream()
    //   .subscribe(
    //     data => {
    //       this.photoStreamIndex = this.photoStreamIndexArray[this.photoDisplayIndex];
    //       this.mainFramePhotoUrl = data[this.photoStreamIndex].fullUrl + this.LARGE_URL_SUFFIX;
    //     }
    //   );
  }
}
