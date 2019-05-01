/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AskAnExpertTestModule } from '../../../test.module';
import { NonDisponibiliteDeleteDialogComponent } from 'app/entities/non-disponibilite/non-disponibilite-delete-dialog.component';
import { NonDisponibiliteService } from 'app/entities/non-disponibilite/non-disponibilite.service';

describe('Component Tests', () => {
    describe('NonDisponibilite Management Delete Component', () => {
        let comp: NonDisponibiliteDeleteDialogComponent;
        let fixture: ComponentFixture<NonDisponibiliteDeleteDialogComponent>;
        let service: NonDisponibiliteService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AskAnExpertTestModule],
                declarations: [NonDisponibiliteDeleteDialogComponent]
            })
                .overrideTemplate(NonDisponibiliteDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NonDisponibiliteDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NonDisponibiliteService);
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
