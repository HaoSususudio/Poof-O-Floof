import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { UserIpLocInfo } from './models.service';

@Injectable({ providedIn: 'root' })

export class LocationService {
  private IPAPICO_URL = 'https://ipapi.co/json';
  private IPGEOLOCCOM_URL = 'https://ipgeolocation.com/?json=1';
  private ipApiCoInfo: UserIpLocInfo;
  private ipGeoLocComInfo: UserIpLocInfo;
  private permissionCoords: string;

  private userIpLocInfo$: BehaviorSubject<UserIpLocInfo> = new BehaviorSubject<UserIpLocInfo>(undefined);

  constructor(private http: HttpClient) {
    this.ipApiCoInfo = new UserIpLocInfo();
    this.ipGeoLocComInfo = new UserIpLocInfo();
    this.getRespFromIpApiCo(); // Main ip geolocation service. 30,000 req/mo.
    this.getRespFromIpGeoLocCom(); // Fallback ip geolocation service. 2 req/s but no monthly limit.
    this.getCoordsByPermission(); // Will overwrite the coords if user provides the fine location.
  }

  pubUserIpLocInfo(): Observable<UserIpLocInfo> {
    if (!this.userIpLocInfo$.value) {
      if (this.ipApiCoInfo) {
        this.userIpLocInfo$.next(this.ipApiCoInfo);
      } else if (this.ipGeoLocComInfo) {
        this.userIpLocInfo$.next(this.ipGeoLocComInfo);
      } else {
        console.log('No ip location service is available.');
      }
    }

    if (this.permissionCoords) {
      this.userIpLocInfo$.value.coords = this.permissionCoords;
    }
    return this.userIpLocInfo$.asObservable();
  }


  getCoordsByPermission() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.permissionCoords = `${position.coords.latitude},${position.coords.longitude}`;
        console.log('Location provided by user.');
      });
    } else {
      console.log('Browser does not support for geolocation');
    }
  }

  getRespFromIpApiCo() {
    this.http.get<IpApiCoJSON>(this.IPAPICO_URL)
      .subscribe(
        data => {
          console.log('Location provided by ipapi.co.');
          this.ipApiCoInfo.ip = data.ip;
          this.ipApiCoInfo.coords = `${data.latitude},${data.longitude}`;
          this.ipApiCoInfo.postal = data.postal;
          this.ipApiCoInfo.city = data.city;
          this.ipApiCoInfo.region = data.region;
          this.ipApiCoInfo.country = data.country_name;
          this.userIpLocInfo$.next(this.ipApiCoInfo);
        },
        err => {
          console.log('Error from ipapi.co');
        }
      );
  }

  getRespFromIpGeoLocCom() {
    this.http.get<IpGeolocComJSON>(this.IPGEOLOCCOM_URL)
      .subscribe(
        data => {
          console.log('Location provided by ipgeolocation.com.');
          this.ipGeoLocComInfo.ip = data.ip;
          this.ipGeoLocComInfo.coords = data.coords;
          this.ipGeoLocComInfo.postal = data.postal;
          this.ipGeoLocComInfo.city = data.city;
          this.ipGeoLocComInfo.region = data.region;
          this.ipGeoLocComInfo.country = data.country;
        },
        err => {
          console.log('Error from ipgeolocation.com');
        }
      );
  }

}

export interface IpGeolocComJSON {
  ip: string;
  city: string;
  region: string;
  country: string;
  coords: string;
  asn: string;
  postal: string;
  timezone: string;
}

export interface IpApiCoJSON {
  ip: string;
  city: string;
  region: string;
  region_code: string;
  country: string;
  country_name: string;
  continent_code: string;
  in_eu: string;
  postal: string;
  latitude: string;
  longitude: string;
  timezone: string;
  utc_offset: string;
  country_calling_code: string;
  currency: string;
  languages: string;
  asn: string;
  org: string;
}
