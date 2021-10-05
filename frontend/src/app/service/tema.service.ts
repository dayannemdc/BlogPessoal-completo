import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
import { Tema } from '../model/Tema';

@Injectable({
  providedIn: 'root'
})
export class TemaService {

  constructor(
    private http: HttpClient
  ) { }


  token = {
    headers: new HttpHeaders().set('Authorization', environment.token)
  }

  getAllTema(): Observable<Tema[]> {
    return this.http.get<Tema[]>('https://blog-pessoal-dayanne.herokuapp.com/api/v1/tema/todos', this.token)
  }

  getByIdTema(id: number): Observable<Tema> {
    return this.http.get<Tema>(`https://blog-pessoal-dayanne.herokuapp.com/api/v1/tema/${id}`, this.token)
  }

  postTema(tema: Tema): Observable<Tema> {
    return this.http.post<Tema>('https://blog-pessoal-dayanne.herokuapp.com/api/v1/tema/salvar', tema, this.token)
  }

  putTema(tema: Tema): Observable<Tema> {
    return this.http.put<Tema>('https://blog-pessoal-dayanne.herokuapp.com/api/v1/tema/atualizar', tema, this.token)
  }

  deleteTema(id: number) {
    return this.http.delete(`https://blog-pessoal-dayanne.herokuapp.com/api/v1/tema/deletar/${id}`, this.token)
  }

}
