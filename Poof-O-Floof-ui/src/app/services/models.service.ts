export class UserIpLocInfo {
  ip: string;
  coords: string;
  postal: string;
  city: string;
  region: string;
  country: string;
}

export class PhotoStreamMetaData {
  lastBundleSize: number;
  totPhotoNum: number;
}

export class AnimalPhotoJSON {
  animalId: number;
  photoId: number;
  type: string;
  fullUrl: string;
}
