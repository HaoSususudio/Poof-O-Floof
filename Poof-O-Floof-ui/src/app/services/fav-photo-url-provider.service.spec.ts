import { TestBed } from '@angular/core/testing';

import { FavPhotoUrlProviderService } from './fav-photo-url-provider.service';

describe('FavPhotoUrlProviderService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FavPhotoUrlProviderService = TestBed.get(FavPhotoUrlProviderService);
    expect(service).toBeTruthy();
  });
});
