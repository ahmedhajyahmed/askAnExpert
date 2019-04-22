import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'jhi-slider',
    templateUrl: './slider.component.html',
    styles: []
})
export class SliderComponent implements OnInit {
    items: Array<any> = [];
    constructor() {
        this.items = [
            { name: 'https://i.pinimg.com/originals/87/22/19/872219e39469e56ff5742581122212bf.jpg' },
            { name: 'https://wallpapertag.com/wallpaper/full/e/4/b/103031-1080-wallpaper-1920x1080-for-xiaomi.jpg' },
            { name: 'https://wallpapercave.com/wp/GoYcMqd.jpg' },
            { name: 'https://i.pinimg.com/originals/87/22/19/872219e39469e56ff5742581122212bf.jpg' },
            { name: 'https://wallpapertag.com/wallpaper/full/e/4/b/103031-1080-wallpaper-1920x1080-for-xiaomi.jpg' },
            { name: 'https://wallpapercave.com/wp/GoYcMqd.jpg' }
        ];
    }

    ngOnInit() {}
}
