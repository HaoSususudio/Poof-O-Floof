import { TestBed } from '@angular/core/testing';

import { PhotoUrlProviderService } from './photo-url-provider.service';

describe('PhotoUrlProviderService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PhotoUrlProviderService = TestBed.get(PhotoUrlProviderService);
    expect(service).toBeTruthy();
  });
});
