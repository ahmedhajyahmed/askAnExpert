import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHistoriqueAppel } from 'app/shared/model/historique-appel.model';
import { HistoriqueAppelService } from './historique-appel.service';

@Component({
    selector: 'jhi-historique-appel-delete-dialog',
    templateUrl: './historique-appel-delete-dialog.component.html'
})
export class HistoriqueAppelDeleteDialogComponent {
    historiqueAppel: IHistoriqueAppel;

    constructor(
        protected historiqueAppelService: HistoriqueAppelService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.historiqueAppelService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'historiqueAppelListModification',
                content: 'Deleted an historiqueAppel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-historique-appel-delete-popup',
    template: ''
})
export class HistoriqueAppelDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ historiqueAppel }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HistoriqueAppelDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.historiqueAppel = historiqueAppel;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/historique-appel', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/historique-appel', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
