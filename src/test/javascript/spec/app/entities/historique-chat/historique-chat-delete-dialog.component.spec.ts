/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AskAnExpertTestModule } from '../../../test.module';
import { HistoriqueChatDeleteDialogComponent } from 'app/entities/historique-chat/historique-chat-delete-dialog.component';
import { HistoriqueChatService } from 'app/entities/historique-chat/historique-chat.service';

describe('Component Tests', () => {
    describe('HistoriqueChat Management Delete Component', () => {
        let comp: HistoriqueChatDeleteDialogComponent;
        let fixture: ComponentFixture<HistoriqueChatDeleteDialogComponent>;
        let service: HistoriqueChatService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AskAnExpertTestModule],
                declarations: [HistoriqueChatDeleteDialogComponent]
            })
                .overrideTemplate(HistoriqueChatDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HistoriqueChatDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HistoriqueChatService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
