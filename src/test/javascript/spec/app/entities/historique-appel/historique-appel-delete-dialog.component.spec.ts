/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AskAnExpertTestModule } from '../../../test.module';
import { HistoriqueAppelDeleteDialogComponent } from 'app/entities/historique-appel/historique-appel-delete-dialog.component';
import { HistoriqueAppelService } from 'app/entities/historique-appel/historique-appel.service';

describe('Component Tests', () => {
    describe('HistoriqueAppel Management Delete Component', () => {
        let comp: HistoriqueAppelDeleteDialogComponent;
        let fixture: ComponentFixture<HistoriqueAppelDeleteDialogComponent>;
        let service: HistoriqueAppelService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AskAnExpertTestModule],
                declarations: [HistoriqueAppelDeleteDialogComponent]
            })
                .overrideTemplate(HistoriqueAppelDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HistoriqueAppelDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HistoriqueAppelService);
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
