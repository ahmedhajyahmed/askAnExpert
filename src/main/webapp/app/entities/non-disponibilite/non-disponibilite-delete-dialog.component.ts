import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INonDisponibilite } from 'app/shared/model/non-disponibilite.model';
import { NonDisponibiliteService } from './non-disponibilite.service';

@Component({
    selector: 'jhi-non-disponibilite-delete-dialog',
    templateUrl: './non-disponibilite-delete-dialog.component.html'
})
export class NonDisponibiliteDeleteDialogComponent {
    nonDisponibilite: INonDisponibilite;

    constructor(
        protected nonDisponibiliteService: NonDisponibiliteService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.nonDisponibiliteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'nonDisponibiliteListModification',
                content: 'Deleted an nonDisponibilite'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-non-disponibilite-delete-popup',
    template: ''
})
export class NonDisponibiliteDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ nonDisponibilite }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(NonDisponibiliteDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.nonDisponibilite = nonDisponibilite;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/non-disponibilite', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/non-disponibilite', { outlets: { popup: null } }]);
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
