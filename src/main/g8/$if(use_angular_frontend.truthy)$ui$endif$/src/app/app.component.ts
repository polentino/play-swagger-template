import {Component} from '@angular/core'
import {NameCounterService} from '@$name;format="lower,snake"$/backend_client_api'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = '$name$';

  constructor(private service: NameCounterService) {
  }

  submit() {
    let countInput = <HTMLInputElement>document.getElementById("countInput");
    let resultDiv = <HTMLDivElement>document.getElementById("result");
    this.service.count(countInput.value).subscribe(result => resultDiv.innerHTML = 'times: ' + result.frequency)
  }
}
