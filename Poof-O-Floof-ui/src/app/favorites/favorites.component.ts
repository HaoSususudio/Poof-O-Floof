import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
export class FavoritesComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  addToFavorites() {
    alert('Photo added to Favorites');
  }
/*<button (click)="addToFavorites()">
   Favorite Photo
</button>
<br />*/

}
