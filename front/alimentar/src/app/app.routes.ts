import { Routes } from '@angular/router';
import { ReportesComponent } from './reportes/reportes.component';
import { NoEncontradoComponent } from './no-encontrado/no-encontrado.component';
import { ColaborarComponent } from './colaborar/colaborar.component';
import { ColaborarDineroComponent } from './colaborar-dinero/colaborar-dinero.component';
import { ColaborarViandaComponent } from './colaborar-vianda/colaborar-vianda.component';
import { ColaborarMoverViandaComponent } from './colaborar-mover-vianda/colaborar-mover-vianda.component';
import { ColaborarRegistroComponent } from './colaborar-registro/colaborar-registro.component';
import { ColaborarHeladeraComponent } from './colaborar-heladera/colaborar-heladera.component';
import { ColaborarOfertaComponent } from './colaborar-oferta/colaborar-oferta.component';
import { FallasComponent } from './fallas/fallas.component';
import { PuntosComponent } from './puntos/puntos.component';
import { MigracionComponent } from './migracion/migracion.component';
import { HeladerasComponent } from './heladeras/heladeras.component';

export const routes: Routes = [
	{path: '', redirectTo: '/colaborar', pathMatch: 'full'},
	{path: 'colaborar', component: ColaborarComponent},
		{path: 'colaborar/dinero', component: ColaborarDineroComponent},
		{path: 'colaborar/vianda', component: ColaborarViandaComponent},
		{path: 'colaborar/mover_vianda', component: ColaborarMoverViandaComponent},
		{path: 'colaborar/registro', component: ColaborarRegistroComponent},
		{path: 'colaborar/heladera', component: ColaborarHeladeraComponent},
		{path: 'colaborar/oferta', component: ColaborarOfertaComponent},
	{path: 'fallas', component: FallasComponent},
	{path: 'puntos', component: PuntosComponent},
	{path: 'migracion', component: MigracionComponent},
	{path: 'heladeras', component: HeladerasComponent},
	{path: 'reportes', component: ReportesComponent},
	{path: '**', component: NoEncontradoComponent},
];
