package simple_petstore_api_yaml

import scala.language.existentials

import play.api.mvc.{Action, Controller, Results}
import play.api.http._
import Results.Status

import de.zalando.play.controllers.{PlayBodyParsing, ParsingError, ResultWrapper}
import PlayBodyParsing._
import scala.util._
import de.zalando.play.controllers.ArrayWrapper

import de.zalando.play.controllers.PlayPathBindables





trait SimplePetstoreApiYamlBase extends Controller with PlayBodyParsing {
    sealed trait AddPetType[T] extends ResultWrapper[T]
    case class AddPet200(result: Pet)(implicit val writer: String => Option[Writeable[Pet]]) extends AddPetType[Pet] { val statusCode = 200 }
    

    private type addPetActionRequestType       = (NewPet)
    private type addPetActionType[T]            = addPetActionRequestType => AddPetType[T] forSome { type T }

        private def addPetParser(acceptedTypes: Seq[String], maxLength: Int = parse.DefaultMaxTextLength) = {
            def bodyMimeType: Option[MediaType] => String = mediaType => {
                val requestType = mediaType.toSeq.map {
                    case m: MediaRange => m
                    case MediaType(a,b,c) => new MediaRange(a,b,c,None,Nil)
                }
                negotiateContent(requestType, acceptedTypes).orElse(acceptedTypes.headOption).getOrElse("application/json")
            }
            
            import de.zalando.play.controllers.WrappedBodyParsers
            
            val customParsers = WrappedBodyParsers.anyParser[NewPet]
            anyParser[NewPet](bodyMimeType, customParsers, "Invalid NewPet", maxLength)
        }

    val addPetActionConstructor  = Action
    def addPetAction[T] = (f: addPetActionType[T]) => addPetActionConstructor(addPetParser(Seq[String]("application/json"))) { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { addPetResponseMimeType =>

            val pet = request.body
            
            

                val result =
                        new PetsPostValidator(pet).errors match {
                            case e if e.isEmpty => processValidaddPetRequest(f)((pet))(addPetResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(addPetResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(406)("The server doesn't support any of the requested mime types"))
    }

    private def processValidaddPetRequest[T](f: addPetActionType[T])(request: addPetActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    abstract class EmptyReturn(override val statusCode: Int = 204) extends ResultWrapper[Results.EmptyContent]  with AddPetType[Results.EmptyContent] { val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NoContent) }
    case object NotImplementedYet extends ResultWrapper[Results.EmptyContent]  with AddPetType[Results.EmptyContent] { val statusCode = 501; val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NotImplemented) }
}
trait DashboardBase extends Controller with PlayBodyParsing {
    sealed trait MethodLevelType[T] extends ResultWrapper[T]
    case class MethodLevel200(result: Seq[Pet])(implicit val writer: String => Option[Writeable[Seq[Pet]]]) extends MethodLevelType[Seq[Pet]] { val statusCode = 200 }
    

    private type methodLevelActionRequestType       = (PetsGetTags, PetsGetLimit)
    private type methodLevelActionType[T]            = methodLevelActionRequestType => MethodLevelType[T] forSome { type T }


    val methodLevelActionConstructor  = Action
    def methodLevelAction[T] = (f: methodLevelActionType[T]) => (tags: PetsGetTags, limit: PetsGetLimit) => methodLevelActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml", "text/xml", "text/html")

        negotiateContent(request.acceptedTypes, providedTypes).map { methodLevelResponseMimeType =>

            
            

                val result =
                        new PetsGetValidator(tags, limit).errors match {
                            case e if e.isEmpty => processValidmethodLevelRequest(f)((tags, limit))(methodLevelResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(methodLevelResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(406)("The server doesn't support any of the requested mime types"))
    }

    private def processValidmethodLevelRequest[T](f: methodLevelActionType[T])(request: methodLevelActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait PathLevelGetType[T] extends ResultWrapper[T]
    case class PathLevelGet200(result: Pet)(implicit val writer: String => Option[Writeable[Pet]]) extends PathLevelGetType[Pet] { val statusCode = 200 }
    

    private type pathLevelGetActionRequestType       = (Long)
    private type pathLevelGetActionType[T]            = pathLevelGetActionRequestType => PathLevelGetType[T] forSome { type T }


    val pathLevelGetActionConstructor  = Action
    def pathLevelGetAction[T] = (f: pathLevelGetActionType[T]) => (id: Long) => pathLevelGetActionConstructor { request =>
        val providedTypes = Seq[String]("application/json", "application/xml", "text/xml", "text/html")

        negotiateContent(request.acceptedTypes, providedTypes).map { pathLevelGetResponseMimeType =>

            
            

                val result =
                        new PetsIdGetValidator(id).errors match {
                            case e if e.isEmpty => processValidpathLevelGetRequest(f)((id))(pathLevelGetResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(pathLevelGetResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(406)("The server doesn't support any of the requested mime types"))
    }

    private def processValidpathLevelGetRequest[T](f: pathLevelGetActionType[T])(request: pathLevelGetActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    sealed trait PathLevelDeleteType[T] extends ResultWrapper[T]
    
    case object PathLevelDelete204 extends EmptyReturn(204)
    

    private type pathLevelDeleteActionRequestType       = (Long)
    private type pathLevelDeleteActionType[T]            = pathLevelDeleteActionRequestType => PathLevelDeleteType[T] forSome { type T }


    val pathLevelDeleteActionConstructor  = Action
    def pathLevelDeleteAction[T] = (f: pathLevelDeleteActionType[T]) => (id: Long) => pathLevelDeleteActionConstructor { request =>
        val providedTypes = Seq[String]("application/json")

        negotiateContent(request.acceptedTypes, providedTypes).map { pathLevelDeleteResponseMimeType =>

            
            

                val result =
                        new PetsIdDeleteValidator(id).errors match {
                            case e if e.isEmpty => processValidpathLevelDeleteRequest(f)((id))(pathLevelDeleteResponseMimeType)
                            case l =>
                                implicit val marshaller: Writeable[Seq[ParsingError]] = parsingErrors2Writable(pathLevelDeleteResponseMimeType)
                                BadRequest(l)
                        }
                result
            
        }.getOrElse(Status(406)("The server doesn't support any of the requested mime types"))
    }

    private def processValidpathLevelDeleteRequest[T](f: pathLevelDeleteActionType[T])(request: pathLevelDeleteActionRequestType)(mimeType: String) = {
      f(request).toResult(mimeType).getOrElse {
        Results.NotAcceptable
      }
    }
    abstract class EmptyReturn(override val statusCode: Int = 204) extends ResultWrapper[Results.EmptyContent]  with MethodLevelType[Results.EmptyContent] with PathLevelGetType[Results.EmptyContent] with PathLevelDeleteType[Results.EmptyContent] { val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NoContent) }
    case object NotImplementedYet extends ResultWrapper[Results.EmptyContent]  with MethodLevelType[Results.EmptyContent] with PathLevelGetType[Results.EmptyContent] with PathLevelDeleteType[Results.EmptyContent] { val statusCode = 501; val result = Results.EmptyContent(); val writer = (x: String) => Some(new DefaultWriteables{}.writeableOf_EmptyContent); override def toResult(mimeType: String): Option[play.api.mvc.Result] = Some(Results.NotImplemented) }
}
