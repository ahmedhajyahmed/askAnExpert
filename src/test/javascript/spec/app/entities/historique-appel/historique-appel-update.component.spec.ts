/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AskAnExpertTestModule } from '../../../test.module';
import { HistoriqueAppelUpdateComponent } from 'app/entities/historique-appel/historique-appel-update.component';
import { HistoriqueAppelService } from 'app/entities/historique-appel/historique-appel.service';
import { HistoriqueAppel } from 'app/shared/model/historique-appel.model';

describe('Component Tests', () => {
    describe('HistoriqueAppel Management Update Component', () => {
        let comp: HistoriqueAppelUpdateComponent;
        let fixture: ComponentFixture<HistoriqueAppelUpdateComponent>;
        let service: HistoriqueAppelService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AskAnExpertTestModule],
                declarations: [HistoriqueAppelUpdateComponent]
            })
                .overrideTemplate(HistoriqueAppelUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HistoriqueAppelUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HistoriqueAppelService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new HistoriqueAppel(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.historiqueAppel = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new HistoriqueAppel();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.historiqueAppel = entity;
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
