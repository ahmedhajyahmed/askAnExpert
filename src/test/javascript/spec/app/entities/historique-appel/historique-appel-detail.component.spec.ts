/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AskAnExpertTestModule } from '../../../test.module';
import { HistoriqueAppelDetailComponent } from 'app/entities/historique-appel/historique-appel-detail.component';
import { HistoriqueAppel } from 'app/shared/model/historique-appel.model';

describe('Component Tests', () => {
    describe('HistoriqueAppel Management Detail Component', () => {
        let comp: HistoriqueAppelDetailComponent;
        let fixture: ComponentFixture<HistoriqueAppelDetailComponent>;
        const route = ({ data: of({ historiqueAppel: new HistoriqueAppel(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AskAnExpertTestModule],
                declarations: [HistoriqueAppelDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HistoriqueAppelDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HistoriqueAppelDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.historiqueAppel).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
