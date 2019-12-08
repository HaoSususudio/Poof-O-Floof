import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LocationService } from './services/location.service';
import { UserIpLocInfo } from './services/models.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  title = 'Poof-O-Floof';
  userIpLocInfo: UserIpLocInfo;

  constructor(
    private http: HttpClient,
    private locService: LocationService
  ) { }

  ngOnInit(): void { this.getUserIpLocInfo(); }

  getUserIpLocInfo() {
    this.locService.getUserIpLocInfo().subscribe(
      (data) => {
        this.userIpLocInfo = data;
      }
    );
  }

}
