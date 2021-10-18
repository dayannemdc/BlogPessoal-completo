import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment.prod';
import { Postagem } from '../model/Postagem';
import { Tema } from '../model/Tema';
import { User } from '../model/User';
import { AuthService } from '../service/auth.service';
import { PostagemService } from '../service/postagem.service';
import { TemaService } from '../service/tema.service';

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css']
})
export class InicioComponent implements OnInit {

  postagem: Postagem = new Postagem()
  tema: Tema = new Tema()
  listaTemas: Tema[]
  listaPostagens: Postagem[]
  idTemaVariavel: number
  user: User = new User()
  userId = environment.idUsuario

  constructor(
    private router: Router,
    private postagemService: PostagemService,
    private temaService: TemaService,
    private authService: AuthService
  ) { }

  ngOnInit() {
    window.scroll(0,0)

    if(environment.token == ''){
      //alert('Sua seção expirou, faça o login novamente.')
      this.router.navigate(['/entrar'])
    }
    this.getAllTemas()
    this.getAllPostagens()
  }

  getAllTemas(){
    this.temaService.getAllTema().subscribe((resp: Tema[])=> {
      this.listaTemas = resp
    })
  }

  findIdTema(){
    this.temaService.getByIdTema(this.idTemaVariavel).subscribe((resp: Tema)=>{
      this.tema = resp
    })
  }

  getAllPostagens(){
    this.postagemService.getAllPostagens().subscribe((resp: Postagem[])=>{
      this.listaPostagens = resp
    })
  }

  findByIdUser(){
    this.authService.getByIdUser(this.userId).subscribe((resp: User)=>{
      this.user = resp
    })
  }

  publicar(){
    this.tema.idTema = this.idTemaVariavel
    this.postagem.temaRelacionado = this.tema

    this.user.idUsuario = this.userId
    this.postagem.criador = this.user

    this.postagemService.postPostagem(this.postagem).subscribe((resp: Postagem)=>{
      this.postagem = resp
      alert('Postagem realizada com sucesso!')
      this.postagem = new Postagem()
      this.getAllPostagens()
    })
  }

}
