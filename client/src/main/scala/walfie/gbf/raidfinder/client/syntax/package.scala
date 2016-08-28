package walfie.gbf.raidfinder.client

import com.thoughtworks.binding
import com.thoughtworks.binding.Binding
import com.thoughtworks.binding.Binding._
import org.scalajs.dom
import org.scalajs.dom.raw.{HTMLElement, HTMLImageElement}
import scala.collection.mutable.Buffer
import scala.scalajs.js

import js.Dynamic.global

package object syntax {
  implicit class HTMLElementOps[T <: HTMLElement](val elem: T) extends AnyVal {
    /** Upgrade element to a Material Design Lite JS-enabled element */
    def mdl(): T = {
      // This is such a hack
      js.timers.setTimeout(1000)(global.componentHandler.upgradeAllRegistered())

      elem
    }

    /** Add a cover background image, slightly darkened */
    def backgroundImage(imageUrl: String, opacity: Double): T = {
      val img = dom.document.createElement("img").asInstanceOf[HTMLImageElement]
      val color = s"rgba(0, 0, 0, $opacity)"
      img.setAttribute("src", imageUrl)
      img.onload = { _: dom.Event =>
        elem.style.backgroundImage = s"linear-gradient($color, $color), url('$imageUrl')"
      }
      elem
    }
  }

  implicit class BufferOps[T](val buffer: Buffer[T]) extends AnyVal {
    /** Overwrite the contents of a buffer */
    def :=(elements: TraversableOnce[T]): Buffer[T] = {
      buffer.clear()
      buffer ++= elements
    }
  }

  implicit class StringOps(val string: String) extends AnyVal {
    def addIf(condition: Boolean, s: String): String =
      if (condition) s"$string $s" else string
  }
}

