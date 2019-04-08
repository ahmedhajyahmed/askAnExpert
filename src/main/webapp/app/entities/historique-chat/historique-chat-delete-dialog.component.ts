import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHistoriqueChat } from 'app/shared/model/historique-chat.model';
import { HistoriqueChatService } from './historique-chat.service';

@Component({
    selector: 'jhi-historique-chat-delete-dialog',
    templateUrl: './historique-chat-delete-dialog.component.html'
})
export class HistoriqueChatDeleteDialogComponent {
    historiqueChat: IHistoriqueChat;

    constructor(
        protected historiqueChatService: HistoriqueChatService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.historiqueChatService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'historiqueChatListModification',
                content: 'Deleted an historiqueChat'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-historique-chat-delete-popup',
    template: ''
})
export class HistoriqueChatDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ historiqueChat }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HistoriqueChatDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.historiqueChat = historiqueChat;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/historique-chat', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/historique-chat', { outlets: { popup: null } }]);
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
