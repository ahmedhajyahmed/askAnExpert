/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AskAnExpertTestModule } from '../../../test.module';
import { HistoriqueChatDetailComponent } from 'app/entities/historique-chat/historique-chat-detail.component';
import { HistoriqueChat } from 'app/shared/model/historique-chat.model';

describe('Component Tests', () => {
    describe('HistoriqueChat Management Detail Component', () => {
        let comp: HistoriqueChatDetailComponent;
        let fixture: ComponentFixture<HistoriqueChatDetailComponent>;
        const route = ({ data: of({ historiqueChat: new HistoriqueChat(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AskAnExpertTestModule],
                declarations: [HistoriqueChatDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HistoriqueChatDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HistoriqueChatDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.historiqueChat).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
