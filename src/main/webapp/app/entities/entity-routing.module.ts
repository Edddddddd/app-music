import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'arreglo',
        data: { pageTitle: 'appMusicApp.arreglo.home.title' },
        loadChildren: () => import('./arreglo/arreglo.module').then(m => m.ArregloModule),
      },
      {
        path: 'user-extra',
        data: { pageTitle: 'appMusicApp.userExtra.home.title' },
        loadChildren: () => import('./user-extra/user-extra.module').then(m => m.UserExtraModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
