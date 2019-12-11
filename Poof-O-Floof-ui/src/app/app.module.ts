import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';

import { MainPhotoComponent } from './main-photo/main-photo.component';
import { SocialMediaComponent } from './social-media/social-media.component';
// import social buttons module
import { JwSocialButtonsModule } from 'jw-angular-social-buttons';
import { TestSocialComponent } from './social-media/test-social/test-social.component';
import { TwitterComponent } from './twitter/twitter.component';
import { FacebookComponent } from './facebook/facebook.component';
import { PinterestComponent } from './pinterest/pinterest.component';
import { NavComponent } from './nav/nav.component';
import { FavoritesComponent } from './favorites/favorites.component';
import { NgxGalleryModule } from 'ngx-gallery';

@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot([
      { path: '', component: MainPhotoComponent },
      { path: 'social-media', component: SocialMediaComponent },
      { path: 'test-social', component: TestSocialComponent },
      { path: 'twitter', component: TwitterComponent },
      { path: 'facebook' , component: FacebookComponent },
      { path: 'pinterest' , component: PinterestComponent },
      { path: 'favorites' , component: FavoritesComponent }
    ]),
    JwSocialButtonsModule,
    NgxGalleryModule
  ],
  declarations: [
    AppComponent,
    MainPhotoComponent,
    SocialMediaComponent,
    TestSocialComponent,
    TwitterComponent,
    FacebookComponent,
    PinterestComponent,
    NavComponent,
    FavoritesComponent
  ],
  bootstrap: [AppComponent],
  providers: []
})
export class AppModule { }
