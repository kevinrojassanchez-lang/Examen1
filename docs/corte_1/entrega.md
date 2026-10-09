# \# Corte 1 — Análisis, diseño y estructura inicial

# 

# \## 8.1 Identificación

# 

# \*\*Caso seleccionado:\*\* Caso 5 — Gestión de citas

# 

# \*\*Integrante 1:\*\* Kevin F. Rojas Sanchez

# 

# \*\*Integrante 2:\*\* No aplica (trabajo individual)

# 

# \*\*URL del repositorio:\*\* https://github.com/kevinrojassanchez-lang/Examen1

# 

# \## 8.2 Descripción del problema

# 

# \*\*Problema que se desea resolver:\*\*

# 

# Se desea desarrollar un sistema de gestión de citas que permita organizar las citas entre clientes y profesionales de una organización. El sistema facilitará el registro de clientes, profesionales y servicios, así como la programación, consulta, reprogramación y cancelación de citas. También permitirá controlar los estados de las citas y evitar conflictos de horarios.

# 

# \*\*Quién utilizaría el sistema:\*\*

# 

# El sistema será utilizado por una persona encargada de administrar las citas, como un recepcionista o administrador. Esta persona podrá registrar la información necesaria y gestionar las citas de los clientes con los profesionales disponibles.

# 

# \*\*Principales operaciones:\*\*

# 

# \- Registrar y consultar clientes.

# \- Registrar y consultar profesionales, sus especialidades y horarios.

# \- Registrar y consultar servicios.

# \- Programar citas para los clientes.

# \- Validar que el profesional esté disponible y pueda ofrecer el servicio solicitado.

# \- Consultar las citas programadas.

# \- Reprogramar o cancelar citas que todavía estén programadas.

# \- Registrar si una cita fue atendida o si el cliente estuvo ausente.

# 

# \*\*Justificación de la elección del caso:\*\*

# 

# Se eligió este caso porque la gestión de citas es una necesidad común en organizaciones que ofrecen servicios profesionales. El sistema permitirá aplicar los principios de la programación orientada a objetos, como la herencia, la abstracción, las interfaces, las enumeraciones y las relaciones entre clases. Además, permite implementar reglas de negocio para evitar citas en fechas pasadas y conflictos de horarios.

# 

# \## 8.3 Requerimientos funcionales

# 

# \*\*RF-01.\*\* El sistema permitirá registrar y consultar clientes con su identificación, nombre, teléfono y correo electrónico.

# 

# \*\*RF-02.\*\* El sistema permitirá registrar y consultar profesionales con su identificación, nombre, teléfono, correo electrónico, especialidad y horario de atención.

# 

# \*\*RF-03.\*\* El sistema permitirá registrar y consultar servicios con su código, nombre, duración y precio.

# 

# \*\*RF-04.\*\* El sistema permitirá programar citas asociando un cliente, un profesional, un servicio y una fecha y hora.

# 

# \*\*RF-05.\*\* El sistema validará que las citas se programen para una fecha y hora futuras.

# 

# \*\*RF-06.\*\* El sistema verificará que el profesional pueda ofrecer el servicio solicitado y que no tenga otra cita en un horario que se traslape.

# 

# \*\*RF-07.\*\* El sistema permitirá reprogramar o cancelar únicamente las citas que se encuentren en estado PROGRAMADA.

# 

# \*\*RF-08.\*\* El sistema permitirá consultar las citas y actualizar su estado a ATENDIDA o AUSENTE cuando corresponda.

# 

# \## 8.4 Identificación de clases

# 

# | Clase | Responsabilidad | Atributos principales |

# |---|---|---|

# | Persona | Representar la información común de las personas. Será una clase abstracta. | identificacion, nombre, telefono, correo |

# | Cliente | Representar a una persona que solicita citas. | Atributos heredados de Persona |

# | Profesional | Representar a un profesional que atiende citas. | especialidad, horario |

# | Servicio | Representar un servicio ofrecido por la organización. | codigo, nombre, duracion, precio |

# | Cita | Representar una cita entre un cliente y un profesional para un servicio. | cliente, profesional, servicio, fechaHora, estado |

# | GestorCitas | Administrar las citas y realizar las validaciones necesarias. | citas |

# | EstadoCita | Definir los estados posibles de una cita. | PROGRAMADA, ATENDIDA, CANCELADA, AUSENTE |

# | Especialidad | Definir las especialidades disponibles para los profesionales. | MEDICINA\_GENERAL, PSICOLOGIA, ODONTOLOGIA, NUTRICION, FISIOTERAPIA |

# | Reprogramable | Definir el comportamiento que permite reprogramar una cita. Es una interfaz. | No posee atributos |

# | HorarioNoDisponibleException | Representar un error cuando un profesional no está disponible. | Mensaje de error heredado de Exception |

# | CitaNoModificableException | Representar un error cuando una cita no puede cancelarse o reprogramarse. | Mensaje de error heredado de Exception |

# 

# \## 8.5 Relaciones entre clases

# 

# | Clases relacionadas | Tipo | Multiplicidad | Explicación |

# |---|---|---|---|

# | Persona y Cliente | Herencia | Una superclase y una subclase | Cliente hereda los atributos y comportamientos comunes de Persona. |

# | Persona y Profesional | Herencia | Una superclase y una subclase | Profesional hereda los atributos y comportamientos comunes de Persona. |

# | Cliente y Cita | Asociación | Un Cliente puede tener cero o muchas Citas; cada Cita tiene un Cliente | Permite identificar a la persona que solicita cada cita. |

# | Profesional y Cita | Asociación | Un Profesional puede atender cero o muchas Citas; cada Cita tiene un Profesional | Permite organizar las citas de cada profesional. |

# | Servicio y Cita | Asociación | Un Servicio puede estar relacionado con cero o muchas Citas; cada Cita corresponde a un Servicio | Permite identificar el servicio solicitado en cada cita. |

# | GestorCitas y Cita | Agregación | Un GestorCitas administra cero o muchas Citas | Permite centralizar la administración y consulta de las citas. |

# | Cita y EstadoCita | Asociación mediante enumeración | Cada Cita tiene un EstadoCita | Permite controlar el estado actual de cada cita. |

# | Profesional y Especialidad | Asociación mediante enumeración | Cada Profesional tiene una Especialidad | Permite clasificar a los profesionales según su especialidad. |

# | Cita y Reprogramable | Implementación de interfaz | Una clase implementa una interfaz | Cita implementa el comportamiento definido por Reprogramable. |

# | GestorCitas y excepciones personalizadas | Dependencia | Un gestor puede utilizar las excepciones cuando corresponda | Permite comunicar errores relacionados con la disponibilidad y modificación de citas. |

# 

# \## 8.6 Herencia o abstracción

# 

# La estructura de herencia propuesta es la siguiente:

# 

# \- Persona (abstracta)

# &#x20; - Cliente

# &#x20; - Profesional

# 

# \*\*Justificación:\*\*

# 

# La clase Persona será abstracta porque representa características comunes de las personas registradas en el sistema, pero no se utilizará directamente para crear objetos. Las clases Cliente y Profesional heredarán sus atributos y podrán incorporar la información específica que necesitan. De esta manera se evita duplicar código y se facilita el mantenimiento del sistema.

# 

# \## 8.7 Interface

# 

# La interfaz utilizada será `Reprogramable`, definida de la siguiente manera:

# 

# ```java

# public interface Reprogramable {

# &#x20;   void reprogramar(java.time.LocalDateTime nuevaFecha);

# }

# ```

# 

# \*\*Clases que la implementarán y por qué:\*\*

# 

# La clase Cita implementará la interfaz Reprogramable porque las citas deben poder cambiar de fecha y hora cuando se encuentren en estado PROGRAMADA y se cumplan las reglas de disponibilidad. La interfaz establece el comportamiento que debe ofrecer una cita que puede reprogramarse.

# 

# \## 8.8 Enumeraciones

# 

# \### EstadoCita

# 

# ```java

# public enum EstadoCita {

# &#x20;   PROGRAMADA,

# &#x20;   ATENDIDA,

# &#x20;   CANCELADA,

# &#x20;   AUSENTE

# }

# ```

# 

# \*\*Uso dentro del sistema:\*\*

# 

# La enumeración EstadoCita permitirá representar los estados posibles de una cita mediante valores definidos. Se utilizará en la clase Cita para conocer si una cita está programada, ya fue atendida, fue cancelada o el cliente estuvo ausente. Esto evita utilizar textos libres que puedan producir errores al registrar los estados.

# 

# \### Especialidad

# 

# También se utilizará una enumeración llamada Especialidad para definir las especialidades que podrán asignarse a los profesionales.

# 

# Sus valores serán:

# 

# \- MEDICINA\_GENERAL

# \- PSICOLOGIA

# \- ODONTOLOGIA

# \- NUTRICION

# \- FISIOTERAPIA

# 

# \## 8.9 UML preliminar

# 

# \*\*Archivo:\*\* `uml.png`

# 

# !\[UML preliminar](uml.png)

# 

# El diagrama UML preliminar representa las clases principales del sistema, sus atributos, sus métodos, sus relaciones y la herencia entre Persona, Cliente y Profesional. También muestra la relación entre Cita, Cliente, Profesional y Servicio, así como la interfaz Reprogramable, las enumeraciones EstadoCita y Especialidad y las excepciones personalizadas.

# 

# La imagen del diagrama se encuentra guardada en la carpeta `docs/corte\_1/` con el nombre `uml.png`.

# 

# \## Código del Corte 1

# 

# Las clases del modelo se encuentran en:

# 

# `proyecto/src/main/java/cr/ac/proyecto/model`

# 

# \- Persona

# \- Cliente

# \- Profesional

# \- Servicio

# \- Cita

# \- GestorCitas

# \- EstadoCita

# \- Especialidad

# \- Reprogramable

# 

# Las excepciones personalizadas se encuentran en:

# 

# `proyecto/src/main/java/cr/ac/proyecto/exception`

# 

# \- HorarioNoDisponibleException

# \- CitaNoModificableException

# 

# \## Distribución del trabajo

# 

# | Integrante | Aportes principales |

# |---|---|

# | Kevin F. Rojas Sanchez | Trabajo individual: análisis del problema, identificación de requerimientos, diseño de clases y relaciones, elaboración del UML y desarrollo de la estructura inicial del sistema. |



