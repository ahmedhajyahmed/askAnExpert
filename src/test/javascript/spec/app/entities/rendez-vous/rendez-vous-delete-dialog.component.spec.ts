/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AskAnExpertTestModule } from '../../../test.module';
import { RendezVousDeleteDialogComponent } from 'app/entities/rendez-vous/rendez-vous-delete-dialog.component';
import { RendezVousService } from 'app/entities/rendez-vous/rendez-vous.service';

describe('Component Tests', () => {
    describe('RendezVous Management Delete Component', () => {
        let comp: RendezVousDeleteDialogComponent;
        let fixture: ComponentFixture<RendezVousDeleteDialogComponent>;
        let service: RendezVousService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AskAnExpertTestModule],
                declarations: [RendezVousDeleteDialogComponent]
            })
                .overrideTemplate(RendezVousDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RendezVousDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RendezVousService);
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
