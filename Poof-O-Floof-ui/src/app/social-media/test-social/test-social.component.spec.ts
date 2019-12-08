import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestSocialComponent } from './test-social.component';

describe('TestSocialComponent', () => {
  let component: TestSocialComponent;
  let fixture: ComponentFixture<TestSocialComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestSocialComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestSocialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
