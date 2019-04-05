import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LoginModalService, AccountService, Account } from 'app/core';
import { Expert } from 'app/shared/model/expert.model';
import { ExpertService } from 'app/entities/expert';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    experts: Expert[] = [];

    constructor(
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private expertService: ExpertService
    ) {}

    loadAll() {
        this.expertService.query().subscribe(
            res => {
                console.log(res.body);
                this.experts = res.body;
            },
            res => {
                console.log(res.toString());
            }
        );
    }

    ngOnInit() {
        this.accountService.identity().then((account: Account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        this.loadAll();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.accountService.identity().then(account => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
}
