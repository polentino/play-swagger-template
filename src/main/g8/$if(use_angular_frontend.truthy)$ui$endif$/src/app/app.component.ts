import {Component} from '@angular/core'
import {NameCounterService} from '@$name;format="lower,snake"$/backend_client_api'
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = '$name$';

  constructor(private service: NameCounterService, private snackBar: MatSnackBar) {
  }

  submit() {
    const countInput = document.getElementById('countInput') as HTMLInputElement;
    const resultDiv = document.getElementById('result') as HTMLDivElement;
    this.service.count(countInput.value)
      .subscribe(
        result => resultDiv.innerHTML = 'times: ' + result.frequency,
        error => {
          if (error.error instanceof ErrorEvent) {
            console.error('An error occurred:', error.error.message);
          } else if (error.status === 429) {
            this.snackBar.open('Too fast; wait a bit ;)', 'OK', {duration:  2000});
          }
        }
      );
  }
}
