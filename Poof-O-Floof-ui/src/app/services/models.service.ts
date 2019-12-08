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

export class UserIpLocInfo {
  ip: string;
  coords: string;
  postal: string;
  city: string;
  region: string;
  country: string;
}

export class PhotoStreamMetaData {
  maxStreamSize: number;
  lastPhotoStartIndex: number;
  lastPhotoBundleSize: number;
}
