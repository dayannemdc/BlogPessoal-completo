import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
import { User } from '../model/User';
import { UserLogin } from '../model/UserLogin';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient
  ) { }

  token = {
    headers: new HttpHeaders().set('Authorization', environment.token)
  }

  entrar(userLogin: UserLogin): Observable<UserLogin>{
    return this.http.put<UserLogin>('https://blog-pessoal-dayanne.herokuapp.com/api/v1/usuario/credenciais', userLogin)
  }

  cadastrar(user: User): Observable<User>{
    return this.http.post<User>('https://blog-pessoal-dayanne.herokuapp.com/api/v1/usuario/salvar', user)
  }

  getByIdUser(id: number): Observable<User>{
    return this.http.get<User>(`https://blog-pessoal-dayanne.herokuapp.com/api/v1/usuario/${id}`, this.token)
  }

  atualizarUser(user: User): Observable<User>{
    return this.http.put<User>('https://blog-pessoal-dayanne.herokuapp.com/api/v1/usuario/atualizar', user, this.token)
  }


  logado() {
    let ok: boolean = false

    if(environment.token != ''){
      ok = true
    }

    return ok
  }
}