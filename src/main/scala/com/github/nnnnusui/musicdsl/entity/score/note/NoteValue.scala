package com.github.nnnnusui.musicdsl.entity
package score.note

import scala.annotation.tailrec

object NoteValue {
  private val _0: NoteValue = Value(0)
  def apply(value: Int*): NoteValue =
    value
      .map(it => Value(it))
      .foldLeft(_0) { (it, sum) => sum + it }

  def unapply(arg: NoteValue): Option[Seq[Seq[Int]]] = {
    @tailrec
    def rec(value: NoteValue): Seq[Int] =
      value match {
        case Value(value)         => Seq(value)
        case Values(values)       => values.map(it => rec(it))
        case HasBase(base, value) => base +: rec(value)
      }
  }

  private case class V(base: Int, value: Int)

  private case class Value(value: Int) extends NoteValue {
    override def percentage: Double =
      value match {
        case 0  => 0.0
        case it => 1.0 / it
      }
  }
  private case class Values(values: Seq[NoteValue]) extends NoteValue {
    override def percentage: Double = values.map(_.percentage).sum
  }
  private case class HasBase(base: NoteValue, value: NoteValue) extends NoteValue {
    override def percentage: Double = value.percentage * base.percentage
  }
}
sealed trait NoteValue {
  def percentage: Double
  private def base(base: NoteValue): NoteValue = NoteValue.HasBase(base, this)

  def +(rhs: NoteValue): NoteValue = NoteValue.Values(Seq(this, rhs))
  def +(rhs: Int): NoteValue = this + NoteValue(rhs)
  def *(rhs: Int): NoteValue = NoteValue.Values(Seq.fill(rhs)(this))
  def /(rhs: NoteValue): NoteValue = this.base(rhs)
  def /(rhs: Int): NoteValue = this / NoteValue(rhs)
}
