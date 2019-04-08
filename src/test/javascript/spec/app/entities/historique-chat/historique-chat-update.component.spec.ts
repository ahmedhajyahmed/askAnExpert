/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AskAnExpertTestModule } from '../../../test.module';
import { HistoriqueChatUpdateComponent } from 'app/entities/historique-chat/historique-chat-update.component';
import { HistoriqueChatService } from 'app/entities/historique-chat/historique-chat.service';
import { HistoriqueChat } from 'app/shared/model/historique-chat.model';

describe('Component Tests', () => {
    describe('HistoriqueChat Management Update Component', () => {
        let comp: HistoriqueChatUpdateComponent;
        let fixture: ComponentFixture<HistoriqueChatUpdateComponent>;
        let service: HistoriqueChatService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AskAnExpertTestModule],
                declarations: [HistoriqueChatUpdateComponent]
            })
                .overrideTemplate(HistoriqueChatUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HistoriqueChatUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HistoriqueChatService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new HistoriqueChat(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.historiqueChat = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new HistoriqueChat();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.historiqueChat = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
