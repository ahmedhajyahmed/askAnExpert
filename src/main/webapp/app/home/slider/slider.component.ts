import { Component, OnInit } from '@angular/core';
import { sliderService } from './slider.service';
import { MethodCall } from '@angular/compiler';
@Component({
    selector: 'jhi-slider',
    templateUrl: './slider.component.html',
    styleUrls: ['slider.component.css']
})
export class SliderComponent implements OnInit {
    experts: any[] = [];
    constructor(private svc: sliderService) {}

    method(): void {
        this.svc.getData().subscribe(data => {
            this.experts = data;
        });
    }
    ngOnInit() {
        this.method();
    }
}
