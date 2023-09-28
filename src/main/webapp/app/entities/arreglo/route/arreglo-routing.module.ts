import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ArregloComponent } from '../list/arreglo.component';
import { ArregloDetailComponent } from '../detail/arreglo-detail.component';
import { ArregloUpdateComponent } from '../update/arreglo-update.component';
import { ArregloRoutingResolveService } from './arreglo-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const arregloRoute: Routes = [
  {
    path: '',
    component: ArregloComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ArregloDetailComponent,
    resolve: {
      arreglo: ArregloRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ArregloUpdateComponent,
    resolve: {
      arreglo: ArregloRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ArregloUpdateComponent,
    resolve: {
      arreglo: ArregloRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(arregloRoute)],
  exports: [RouterModule],
})
export class ArregloRoutingModule {}
